import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyLocation } from './company-location.model';
import { CompanyLocationService } from './company-location.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-location',
    templateUrl: './company-location.component.html'
})
export class CompanyLocationComponent implements OnInit, OnDestroy {
companyLocations: CompanyLocation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyLocationService: CompanyLocationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyLocationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyLocations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyLocation) {
        return item.id;
    }
    registerChangeInCompanyLocations() {
        this.eventSubscriber = this.eventManager.subscribe('companyLocationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
