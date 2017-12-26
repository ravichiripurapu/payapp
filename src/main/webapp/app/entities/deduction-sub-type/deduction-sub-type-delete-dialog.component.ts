import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeductionSubType } from './deduction-sub-type.model';
import { DeductionSubTypePopupService } from './deduction-sub-type-popup.service';
import { DeductionSubTypeService } from './deduction-sub-type.service';

@Component({
    selector: 'jhi-deduction-sub-type-delete-dialog',
    templateUrl: './deduction-sub-type-delete-dialog.component.html'
})
export class DeductionSubTypeDeleteDialogComponent {

    deductionSubType: DeductionSubType;

    constructor(
        private deductionSubTypeService: DeductionSubTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deductionSubTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deductionSubTypeListModification',
                content: 'Deleted an deductionSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deduction-sub-type-delete-popup',
    template: ''
})
export class DeductionSubTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deductionSubTypePopupService: DeductionSubTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.deductionSubTypePopupService
                .open(DeductionSubTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
