import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BankAccountType } from './bank-account-type.model';
import { BankAccountTypePopupService } from './bank-account-type-popup.service';
import { BankAccountTypeService } from './bank-account-type.service';

@Component({
    selector: 'jhi-bank-account-type-delete-dialog',
    templateUrl: './bank-account-type-delete-dialog.component.html'
})
export class BankAccountTypeDeleteDialogComponent {

    bankAccountType: BankAccountType;

    constructor(
        private bankAccountTypeService: BankAccountTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bankAccountTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bankAccountTypeListModification',
                content: 'Deleted an bankAccountType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bank-account-type-delete-popup',
    template: ''
})
export class BankAccountTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bankAccountTypePopupService: BankAccountTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bankAccountTypePopupService
                .open(BankAccountTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
