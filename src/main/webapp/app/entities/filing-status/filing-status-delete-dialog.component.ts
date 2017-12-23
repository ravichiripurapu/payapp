import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FilingStatus } from './filing-status.model';
import { FilingStatusPopupService } from './filing-status-popup.service';
import { FilingStatusService } from './filing-status.service';

@Component({
    selector: 'jhi-filing-status-delete-dialog',
    templateUrl: './filing-status-delete-dialog.component.html'
})
export class FilingStatusDeleteDialogComponent {

    filingStatus: FilingStatus;

    constructor(
        private filingStatusService: FilingStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filingStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filingStatusListModification',
                content: 'Deleted an filingStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filing-status-delete-popup',
    template: ''
})
export class FilingStatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filingStatusPopupService: FilingStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.filingStatusPopupService
                .open(FilingStatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
