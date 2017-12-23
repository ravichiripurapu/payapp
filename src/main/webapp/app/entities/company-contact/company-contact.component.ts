import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyContact } from './company-contact.model';
import { CompanyContactService } from './company-contact.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-contact',
    templateUrl: './company-contact.component.html'
})
export class CompanyContactComponent implements OnInit, OnDestroy {
companyContacts: CompanyContact[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyContactService: CompanyContactService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyContactService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyContacts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyContacts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyContact) {
        return item.id;
    }
    registerChangeInCompanyContacts() {
        this.eventSubscriber = this.eventManager.subscribe('companyContactListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
