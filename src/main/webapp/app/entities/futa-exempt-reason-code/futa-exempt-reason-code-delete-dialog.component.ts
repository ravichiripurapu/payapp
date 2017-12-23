import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FutaExemptReasonCode } from './futa-exempt-reason-code.model';
import { FutaExemptReasonCodePopupService } from './futa-exempt-reason-code-popup.service';
import { FutaExemptReasonCodeService } from './futa-exempt-reason-code.service';

@Component({
    selector: 'jhi-futa-exempt-reason-code-delete-dialog',
    templateUrl: './futa-exempt-reason-code-delete-dialog.component.html'
})
export class FutaExemptReasonCodeDeleteDialogComponent {

    futaExemptReasonCode: FutaExemptReasonCode;

    constructor(
        private futaExemptReasonCodeService: FutaExemptReasonCodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.futaExemptReasonCodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'futaExemptReasonCodeListModification',
                content: 'Deleted an futaExemptReasonCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-futa-exempt-reason-code-delete-popup',
    template: ''
})
export class FutaExemptReasonCodeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private futaExemptReasonCodePopupService: FutaExemptReasonCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.futaExemptReasonCodePopupService
                .open(FutaExemptReasonCodeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
