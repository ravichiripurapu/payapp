/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeTaxesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes-delete-dialog.component';
import { PayrollEmployeeTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.service';

describe('Component Tests', () => {

    describe('PayrollEmployeeTaxes Management Delete Component', () => {
        let comp: PayrollEmployeeTaxesDeleteDialogComponent;
        let fixture: ComponentFixture<PayrollEmployeeTaxesDeleteDialogComponent>;
        let service: PayrollEmployeeTaxesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeTaxesDeleteDialogComponent],
                providers: [
                    PayrollEmployeeTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployeeTaxesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeTaxesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeTaxesService);
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
