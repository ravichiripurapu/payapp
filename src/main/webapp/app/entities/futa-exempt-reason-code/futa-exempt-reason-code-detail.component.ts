import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FutaExemptReasonCode } from './futa-exempt-reason-code.model';
import { FutaExemptReasonCodeService } from './futa-exempt-reason-code.service';

@Component({
    selector: 'jhi-futa-exempt-reason-code-detail',
    templateUrl: './futa-exempt-reason-code-detail.component.html'
})
export class FutaExemptReasonCodeDetailComponent implements OnInit, OnDestroy {

    futaExemptReasonCode: FutaExemptReasonCode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private futaExemptReasonCodeService: FutaExemptReasonCodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFutaExemptReasonCodes();
    }

    load(id) {
        this.futaExemptReasonCodeService.find(id).subscribe((futaExemptReasonCode) => {
            this.futaExemptReasonCode = futaExemptReasonCode;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFutaExemptReasonCodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'futaExemptReasonCodeListModification',
            (response) => this.load(this.futaExemptReasonCode.id)
        );
    }
}
