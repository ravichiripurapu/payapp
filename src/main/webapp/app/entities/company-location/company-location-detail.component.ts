import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyLocation } from './company-location.model';
import { CompanyLocationService } from './company-location.service';

@Component({
    selector: 'jhi-company-location-detail',
    templateUrl: './company-location-detail.component.html'
})
export class CompanyLocationDetailComponent implements OnInit, OnDestroy {

    companyLocation: CompanyLocation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyLocationService: CompanyLocationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyLocations();
    }

    load(id) {
        this.companyLocationService.find(id).subscribe((companyLocation) => {
            this.companyLocation = companyLocation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyLocationListModification',
            (response) => this.load(this.companyLocation.id)
        );
    }
}
