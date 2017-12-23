import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LocalTaxes } from './local-taxes.model';
import { LocalTaxesPopupService } from './local-taxes-popup.service';
import { LocalTaxesService } from './local-taxes.service';

@Component({
    selector: 'jhi-local-taxes-delete-dialog',
    templateUrl: './local-taxes-delete-dialog.component.html'
})
export class LocalTaxesDeleteDialogComponent {

    localTaxes: LocalTaxes;

    constructor(
        private localTaxesService: LocalTaxesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.localTaxesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'localTaxesListModification',
                content: 'Deleted an localTaxes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-local-taxes-delete-popup',
    template: ''
})
export class LocalTaxesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private localTaxesPopupService: LocalTaxesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.localTaxesPopupService
                .open(LocalTaxesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
