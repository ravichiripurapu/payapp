import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyCompensation } from './company-compensation.model';
import { CompanyCompensationService } from './company-compensation.service';

@Component({
    selector: 'jhi-company-compensation-detail',
    templateUrl: './company-compensation-detail.component.html'
})
export class CompanyCompensationDetailComponent implements OnInit, OnDestroy {

    companyCompensation: CompanyCompensation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyCompensationService: CompanyCompensationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyCompensations();
    }

    load(id) {
        this.companyCompensationService.find(id).subscribe((companyCompensation) => {
            this.companyCompensation = companyCompensation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyCompensations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyCompensationListModification',
            (response) => this.load(this.companyCompensation.id)
        );
    }
}
