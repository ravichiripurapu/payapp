import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyContactType } from './company-contact-type.model';
import { CompanyContactTypePopupService } from './company-contact-type-popup.service';
import { CompanyContactTypeService } from './company-contact-type.service';

@Component({
    selector: 'jhi-company-contact-type-delete-dialog',
    templateUrl: './company-contact-type-delete-dialog.component.html'
})
export class CompanyContactTypeDeleteDialogComponent {

    companyContactType: CompanyContactType;

    constructor(
        private companyContactTypeService: CompanyContactTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyContactTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyContactTypeListModification',
                content: 'Deleted an companyContactType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-contact-type-delete-popup',
    template: ''
})
export class CompanyContactTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyContactTypePopupService: CompanyContactTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyContactTypePopupService
                .open(CompanyContactTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
