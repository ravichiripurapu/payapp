/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployerTaxesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes-delete-dialog.component';
import { PayrollEmployerTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.service';

describe('Component Tests', () => {

    describe('PayrollEmployerTaxes Management Delete Component', () => {
        let comp: PayrollEmployerTaxesDeleteDialogComponent;
        let fixture: ComponentFixture<PayrollEmployerTaxesDeleteDialogComponent>;
        let service: PayrollEmployerTaxesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployerTaxesDeleteDialogComponent],
                providers: [
                    PayrollEmployerTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployerTaxesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployerTaxesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployerTaxesService);
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
