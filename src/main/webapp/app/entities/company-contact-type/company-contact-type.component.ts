import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyContactType } from './company-contact-type.model';
import { CompanyContactTypeService } from './company-contact-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-contact-type',
    templateUrl: './company-contact-type.component.html'
})
export class CompanyContactTypeComponent implements OnInit, OnDestroy {
companyContactTypes: CompanyContactType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyContactTypeService: CompanyContactTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyContactTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyContactTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyContactTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyContactType) {
        return item.id;
    }
    registerChangeInCompanyContactTypes() {
        this.eventSubscriber = this.eventManager.subscribe('companyContactTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
