import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { QuarterlyReports } from './quarterly-reports.model';
import { QuarterlyReportsPopupService } from './quarterly-reports-popup.service';
import { QuarterlyReportsService } from './quarterly-reports.service';

@Component({
    selector: 'jhi-quarterly-reports-delete-dialog',
    templateUrl: './quarterly-reports-delete-dialog.component.html'
})
export class QuarterlyReportsDeleteDialogComponent {

    quarterlyReports: QuarterlyReports;

    constructor(
        private quarterlyReportsService: QuarterlyReportsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quarterlyReportsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'quarterlyReportsListModification',
                content: 'Deleted an quarterlyReports'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quarterly-reports-delete-popup',
    template: ''
})
export class QuarterlyReportsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quarterlyReportsPopupService: QuarterlyReportsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.quarterlyReportsPopupService
                .open(QuarterlyReportsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
