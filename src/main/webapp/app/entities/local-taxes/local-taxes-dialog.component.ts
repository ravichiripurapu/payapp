import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LocalTaxes } from './local-taxes.model';
import { LocalTaxesPopupService } from './local-taxes-popup.service';
import { LocalTaxesService } from './local-taxes.service';

@Component({
    selector: 'jhi-local-taxes-dialog',
    templateUrl: './local-taxes-dialog.component.html'
})
export class LocalTaxesDialogComponent implements OnInit {

    localTaxes: LocalTaxes;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private localTaxesService: LocalTaxesService,
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
        if (this.localTaxes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.localTaxesService.update(this.localTaxes));
        } else {
            this.subscribeToSaveResponse(
                this.localTaxesService.create(this.localTaxes));
        }
    }

    private subscribeToSaveResponse(result: Observable<LocalTaxes>) {
        result.subscribe((res: LocalTaxes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LocalTaxes) {
        this.eventManager.broadcast({ name: 'localTaxesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-local-taxes-popup',
    template: ''
})
export class LocalTaxesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private localTaxesPopupService: LocalTaxesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.localTaxesPopupService
                    .open(LocalTaxesDialogComponent as Component, params['id']);
            } else {
                this.localTaxesPopupService
                    .open(LocalTaxesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
