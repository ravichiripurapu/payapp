import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompensationType } from './compensation-type.model';
import { CompensationTypePopupService } from './compensation-type-popup.service';
import { CompensationTypeService } from './compensation-type.service';

@Component({
    selector: 'jhi-compensation-type-dialog',
    templateUrl: './compensation-type-dialog.component.html'
})
export class CompensationTypeDialogComponent implements OnInit {

    compensationType: CompensationType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private compensationTypeService: CompensationTypeService,
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
        if (this.compensationType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.compensationTypeService.update(this.compensationType));
        } else {
            this.subscribeToSaveResponse(
                this.compensationTypeService.create(this.compensationType));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompensationType>) {
        result.subscribe((res: CompensationType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompensationType) {
        this.eventManager.broadcast({ name: 'compensationTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-compensation-type-popup',
    template: ''
})
export class CompensationTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compensationTypePopupService: CompensationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.compensationTypePopupService
                    .open(CompensationTypeDialogComponent as Component, params['id']);
            } else {
                this.compensationTypePopupService
                    .open(CompensationTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
