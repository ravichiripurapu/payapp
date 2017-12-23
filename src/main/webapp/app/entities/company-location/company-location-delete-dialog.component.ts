import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyLocation } from './company-location.model';
import { CompanyLocationPopupService } from './company-location-popup.service';
import { CompanyLocationService } from './company-location.service';

@Component({
    selector: 'jhi-company-location-delete-dialog',
    templateUrl: './company-location-delete-dialog.component.html'
})
export class CompanyLocationDeleteDialogComponent {

    companyLocation: CompanyLocation;

    constructor(
        private companyLocationService: CompanyLocationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyLocationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyLocationListModification',
                content: 'Deleted an companyLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-location-delete-popup',
    template: ''
})
export class CompanyLocationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyLocationPopupService: CompanyLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyLocationPopupService
                .open(CompanyLocationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
