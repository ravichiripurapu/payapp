import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeductionType } from './deduction-type.model';
import { DeductionTypePopupService } from './deduction-type-popup.service';
import { DeductionTypeService } from './deduction-type.service';

@Component({
    selector: 'jhi-deduction-type-delete-dialog',
    templateUrl: './deduction-type-delete-dialog.component.html'
})
export class DeductionTypeDeleteDialogComponent {

    deductionType: DeductionType;

    constructor(
        private deductionTypeService: DeductionTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deductionTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deductionTypeListModification',
                content: 'Deleted an deductionType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deduction-type-delete-popup',
    template: ''
})
export class DeductionTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deductionTypePopupService: DeductionTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.deductionTypePopupService
                .open(DeductionTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
