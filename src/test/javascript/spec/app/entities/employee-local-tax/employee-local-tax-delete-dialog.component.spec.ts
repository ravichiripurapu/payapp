/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeLocalTaxDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax-delete-dialog.component';
import { EmployeeLocalTaxService } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.service';

describe('Component Tests', () => {

    describe('EmployeeLocalTax Management Delete Component', () => {
        let comp: EmployeeLocalTaxDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeLocalTaxDeleteDialogComponent>;
        let service: EmployeeLocalTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeLocalTaxDeleteDialogComponent],
                providers: [
                    EmployeeLocalTaxService
                ]
            })
            .overrideTemplate(EmployeeLocalTaxDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeLocalTaxDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeLocalTaxService);
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
