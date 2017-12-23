/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeFederalTaxDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax-delete-dialog.component';
import { EmployeeFederalTaxService } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.service';

describe('Component Tests', () => {

    describe('EmployeeFederalTax Management Delete Component', () => {
        let comp: EmployeeFederalTaxDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeFederalTaxDeleteDialogComponent>;
        let service: EmployeeFederalTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeFederalTaxDeleteDialogComponent],
                providers: [
                    EmployeeFederalTaxService
                ]
            })
            .overrideTemplate(EmployeeFederalTaxDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeFederalTaxDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeFederalTaxService);
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
