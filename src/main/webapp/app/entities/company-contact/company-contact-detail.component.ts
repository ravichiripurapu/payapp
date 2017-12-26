import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyContact } from './company-contact.model';
import { CompanyContactService } from './company-contact.service';

@Component({
    selector: 'jhi-company-contact-detail',
    templateUrl: './company-contact-detail.component.html'
})
export class CompanyContactDetailComponent implements OnInit, OnDestroy {

    companyContact: CompanyContact;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyContactService: CompanyContactService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyContacts();
    }

    load(id) {
        this.companyContactService.find(id).subscribe((companyContact) => {
            this.companyContact = companyContact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyContacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyContactListModification',
            (response) => this.load(this.companyContact.id)
        );
    }
}
