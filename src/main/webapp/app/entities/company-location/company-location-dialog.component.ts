import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyLocation } from './company-location.model';
import { CompanyLocationPopupService } from './company-location-popup.service';
import { CompanyLocationService } from './company-location.service';

@Component({
    selector: 'jhi-company-location-dialog',
    templateUrl: './company-location-dialog.component.html'
})
export class CompanyLocationDialogComponent implements OnInit {

    companyLocation: CompanyLocation;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyLocationService: CompanyLocationService,
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
        if (this.companyLocation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyLocationService.update(this.companyLocation));
        } else {
            this.subscribeToSaveResponse(
                this.companyLocationService.create(this.companyLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyLocation>) {
        result.subscribe((res: CompanyLocation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyLocation) {
        this.eventManager.broadcast({ name: 'companyLocationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-location-popup',
    template: ''
})
export class CompanyLocationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyLocationPopupService: CompanyLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyLocationPopupService
                    .open(CompanyLocationDialogComponent as Component, params['id']);
            } else {
                this.companyLocationPopupService
                    .open(CompanyLocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
