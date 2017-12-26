import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyContact } from './company-contact.model';
import { CompanyContactPopupService } from './company-contact-popup.service';
import { CompanyContactService } from './company-contact.service';

@Component({
    selector: 'jhi-company-contact-dialog',
    templateUrl: './company-contact-dialog.component.html'
})
export class CompanyContactDialogComponent implements OnInit {

    companyContact: CompanyContact;
    isSaving: boolean;
    dobDp: any;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyContactService: CompanyContactService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyContact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyContactService.update(this.companyContact));
        } else {
            this.subscribeToSaveResponse(
                this.companyContactService.create(this.companyContact));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyContact>) {
        result.subscribe((res: CompanyContact) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyContact) {
        this.eventManager.broadcast({ name: 'companyContactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-contact-popup',
    template: ''
})
export class CompanyContactPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyContactPopupService: CompanyContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyContactPopupService
                    .open(CompanyContactDialogComponent as Component, params['id']);
            } else {
                this.companyContactPopupService
                    .open(CompanyContactDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
