import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyCompensation } from './company-compensation.model';
import { CompanyCompensationPopupService } from './company-compensation-popup.service';
import { CompanyCompensationService } from './company-compensation.service';

@Component({
    selector: 'jhi-company-compensation-delete-dialog',
    templateUrl: './company-compensation-delete-dialog.component.html'
})
export class CompanyCompensationDeleteDialogComponent {

    companyCompensation: CompanyCompensation;

    constructor(
        private companyCompensationService: CompanyCompensationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyCompensationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyCompensationListModification',
                content: 'Deleted an companyCompensation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-compensation-delete-popup',
    template: ''
})
export class CompanyCompensationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyCompensationPopupService: CompanyCompensationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyCompensationPopupService
                .open(CompanyCompensationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
