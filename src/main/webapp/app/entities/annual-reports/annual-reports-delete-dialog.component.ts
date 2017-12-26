import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnnualReports } from './annual-reports.model';
import { AnnualReportsPopupService } from './annual-reports-popup.service';
import { AnnualReportsService } from './annual-reports.service';

@Component({
    selector: 'jhi-annual-reports-delete-dialog',
    templateUrl: './annual-reports-delete-dialog.component.html'
})
export class AnnualReportsDeleteDialogComponent {

    annualReports: AnnualReports;

    constructor(
        private annualReportsService: AnnualReportsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.annualReportsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'annualReportsListModification',
                content: 'Deleted an annualReports'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-annual-reports-delete-popup',
    template: ''
})
export class AnnualReportsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private annualReportsPopupService: AnnualReportsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.annualReportsPopupService
                .open(AnnualReportsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
