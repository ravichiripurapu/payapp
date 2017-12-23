import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyContact } from './company-contact.model';
import { CompanyContactPopupService } from './company-contact-popup.service';
import { CompanyContactService } from './company-contact.service';

@Component({
    selector: 'jhi-company-contact-delete-dialog',
    templateUrl: './company-contact-delete-dialog.component.html'
})
export class CompanyContactDeleteDialogComponent {

    companyContact: CompanyContact;

    constructor(
        private companyContactService: CompanyContactService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyContactService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyContactListModification',
                content: 'Deleted an companyContact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-contact-delete-popup',
    template: ''
})
export class CompanyContactDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyContactPopupService: CompanyContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyContactPopupService
                .open(CompanyContactDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
