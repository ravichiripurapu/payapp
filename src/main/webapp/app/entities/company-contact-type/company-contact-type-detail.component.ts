import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyContactType } from './company-contact-type.model';
import { CompanyContactTypeService } from './company-contact-type.service';

@Component({
    selector: 'jhi-company-contact-type-detail',
    templateUrl: './company-contact-type-detail.component.html'
})
export class CompanyContactTypeDetailComponent implements OnInit, OnDestroy {

    companyContactType: CompanyContactType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyContactTypeService: CompanyContactTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyContactTypes();
    }

    load(id) {
        this.companyContactTypeService.find(id).subscribe((companyContactType) => {
            this.companyContactType = companyContactType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyContactTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyContactTypeListModification',
            (response) => this.load(this.companyContactType.id)
        );
    }
}
