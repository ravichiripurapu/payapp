import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DepositFrequency } from './deposit-frequency.model';
import { DepositFrequencyService } from './deposit-frequency.service';

@Component({
    selector: 'jhi-deposit-frequency-detail',
    templateUrl: './deposit-frequency-detail.component.html'
})
export class DepositFrequencyDetailComponent implements OnInit, OnDestroy {

    depositFrequency: DepositFrequency;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private depositFrequencyService: DepositFrequencyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDepositFrequencies();
    }

    load(id) {
        this.depositFrequencyService.find(id).subscribe((depositFrequency) => {
            this.depositFrequency = depositFrequency;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDepositFrequencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'depositFrequencyListModification',
            (response) => this.load(this.depositFrequency.id)
        );
    }
}
