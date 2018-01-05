import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyEarning } from './company-earning.model';
import { CompanyEarningService } from './company-earning.service';

@Component({
    selector: 'jhi-company-earning-detail',
    templateUrl: './company-earning-detail.component.html'
})
export class CompanyEarningDetailComponent implements OnInit, OnDestroy {

    companyEarningType: CompanyEarning;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyEarningService: CompanyEarningService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyEarnings();
    }

    load(id) {
        this.companyEarningService.find(id).subscribe((companyEarningType) => {
            this.companyEarningType = companyEarningType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyEarnings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyEarningListModification',
            (response) => this.load(this.companyEarningType.id)
        );
    }
}
