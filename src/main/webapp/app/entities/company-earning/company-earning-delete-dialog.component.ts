import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyEarning } from './company-earning.model';
import { CompanyEarningPopupService } from './company-earning-popup.service';
import { CompanyEarningService } from './company-earning.service';

@Component({
    selector: 'jhi-company-earning-delete-dialog',
    templateUrl: './company-earning-delete-dialog.component.html'
})
export class CompanyEarningDeleteDialogComponent {

    companyEarningType: CompanyEarning;

    constructor(
        private companyEarningService: CompanyEarningService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyEarningService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyEarningListModification',
                content: 'Deleted an companyEarningType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-earning-delete-popup',
    template: ''
})
export class CompanyEarningDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyEarningPopupService: CompanyEarningPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyEarningPopupService
                .open(CompanyEarningDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
