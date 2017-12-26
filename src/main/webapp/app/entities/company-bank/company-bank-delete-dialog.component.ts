import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyBank } from './company-bank.model';
import { CompanyBankPopupService } from './company-bank-popup.service';
import { CompanyBankService } from './company-bank.service';

@Component({
    selector: 'jhi-company-bank-delete-dialog',
    templateUrl: './company-bank-delete-dialog.component.html'
})
export class CompanyBankDeleteDialogComponent {

    companyBank: CompanyBank;

    constructor(
        private companyBankService: CompanyBankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyBankService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyBankListModification',
                content: 'Deleted an companyBank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-bank-delete-popup',
    template: ''
})
export class CompanyBankDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyBankPopupService: CompanyBankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyBankPopupService
                .open(CompanyBankDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
