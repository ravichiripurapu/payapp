import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Gender } from './gender.model';
import { GenderService } from './gender.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-gender',
    templateUrl: './gender.component.html'
})
export class GenderComponent implements OnInit, OnDestroy {
genders: Gender[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private genderService: GenderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.genderService.query().subscribe(
            (res: ResponseWrapper) => {
                this.genders = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGenders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Gender) {
        return item.id;
    }
    registerChangeInGenders() {
        this.eventSubscriber = this.eventManager.subscribe('genderListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
