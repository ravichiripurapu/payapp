import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DepositType } from './deposit-type.model';
import { DepositTypePopupService } from './deposit-type-popup.service';
import { DepositTypeService } from './deposit-type.service';

@Component({
    selector: 'jhi-deposit-type-delete-dialog',
    templateUrl: './deposit-type-delete-dialog.component.html'
})
export class DepositTypeDeleteDialogComponent {

    depositType: DepositType;

    constructor(
        private depositTypeService: DepositTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depositTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'depositTypeListModification',
                content: 'Deleted an depositType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deposit-type-delete-popup',
    template: ''
})
export class DepositTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private depositTypePopupService: DepositTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.depositTypePopupService
                .open(DepositTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
