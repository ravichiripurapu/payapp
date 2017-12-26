/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { DepositFrequencyDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency-delete-dialog.component';
import { DepositFrequencyService } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.service';

describe('Component Tests', () => {

    describe('DepositFrequency Management Delete Component', () => {
        let comp: DepositFrequencyDeleteDialogComponent;
        let fixture: ComponentFixture<DepositFrequencyDeleteDialogComponent>;
        let service: DepositFrequencyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositFrequencyDeleteDialogComponent],
                providers: [
                    DepositFrequencyService
                ]
            })
            .overrideTemplate(DepositFrequencyDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositFrequencyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositFrequencyService);
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
