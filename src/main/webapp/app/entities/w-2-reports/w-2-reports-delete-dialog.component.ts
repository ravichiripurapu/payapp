import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { W2Reports } from './w-2-reports.model';
import { W2ReportsPopupService } from './w-2-reports-popup.service';
import { W2ReportsService } from './w-2-reports.service';

@Component({
    selector: 'jhi-w-2-reports-delete-dialog',
    templateUrl: './w-2-reports-delete-dialog.component.html'
})
export class W2ReportsDeleteDialogComponent {

    w2Reports: W2Reports;

    constructor(
        private w2ReportsService: W2ReportsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.w2ReportsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'w2ReportsListModification',
                content: 'Deleted an w2Reports'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-w-2-reports-delete-popup',
    template: ''
})
export class W2ReportsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private w2ReportsPopupService: W2ReportsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.w2ReportsPopupService
                .open(W2ReportsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
