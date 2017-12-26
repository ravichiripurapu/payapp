import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FilingStatus } from './filing-status.model';
import { FilingStatusService } from './filing-status.service';

@Component({
    selector: 'jhi-filing-status-detail',
    templateUrl: './filing-status-detail.component.html'
})
export class FilingStatusDetailComponent implements OnInit, OnDestroy {

    filingStatus: FilingStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private filingStatusService: FilingStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilingStatuses();
    }

    load(id) {
        this.filingStatusService.find(id).subscribe((filingStatus) => {
            this.filingStatus = filingStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilingStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filingStatusListModification',
            (response) => this.load(this.filingStatus.id)
        );
    }
}
