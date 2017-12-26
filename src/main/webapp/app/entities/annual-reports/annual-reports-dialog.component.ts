import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnnualReports } from './annual-reports.model';
import { AnnualReportsPopupService } from './annual-reports-popup.service';
import { AnnualReportsService } from './annual-reports.service';

@Component({
    selector: 'jhi-annual-reports-dialog',
    templateUrl: './annual-reports-dialog.component.html'
})
export class AnnualReportsDialogComponent implements OnInit {

    annualReports: AnnualReports;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private annualReportsService: AnnualReportsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.annualReports.id !== undefined) {
            this.subscribeToSaveResponse(
                this.annualReportsService.update(this.annualReports));
        } else {
            this.subscribeToSaveResponse(
                this.annualReportsService.create(this.annualReports));
        }
    }

    private subscribeToSaveResponse(result: Observable<AnnualReports>) {
        result.subscribe((res: AnnualReports) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AnnualReports) {
        this.eventManager.broadcast({ name: 'annualReportsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-annual-reports-popup',
    template: ''
})
export class AnnualReportsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private annualReportsPopupService: AnnualReportsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.annualReportsPopupService
                    .open(AnnualReportsDialogComponent as Component, params['id']);
            } else {
                this.annualReportsPopupService
                    .open(AnnualReportsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
