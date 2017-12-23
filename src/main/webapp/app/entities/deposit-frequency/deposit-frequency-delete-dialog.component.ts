import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DepositFrequency } from './deposit-frequency.model';
import { DepositFrequencyPopupService } from './deposit-frequency-popup.service';
import { DepositFrequencyService } from './deposit-frequency.service';

@Component({
    selector: 'jhi-deposit-frequency-delete-dialog',
    templateUrl: './deposit-frequency-delete-dialog.component.html'
})
export class DepositFrequencyDeleteDialogComponent {

    depositFrequency: DepositFrequency;

    constructor(
        private depositFrequencyService: DepositFrequencyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depositFrequencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'depositFrequencyListModification',
                content: 'Deleted an depositFrequency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deposit-frequency-delete-popup',
    template: ''
})
export class DepositFrequencyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private depositFrequencyPopupService: DepositFrequencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.depositFrequencyPopupService
                .open(DepositFrequencyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
