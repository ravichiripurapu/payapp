/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollFrequencyDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency-delete-dialog.component';
import { PayrollFrequencyService } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.service';

describe('Component Tests', () => {

    describe('PayrollFrequency Management Delete Component', () => {
        let comp: PayrollFrequencyDeleteDialogComponent;
        let fixture: ComponentFixture<PayrollFrequencyDeleteDialogComponent>;
        let service: PayrollFrequencyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollFrequencyDeleteDialogComponent],
                providers: [
                    PayrollFrequencyService
                ]
            })
            .overrideTemplate(PayrollFrequencyDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollFrequencyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollFrequencyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
