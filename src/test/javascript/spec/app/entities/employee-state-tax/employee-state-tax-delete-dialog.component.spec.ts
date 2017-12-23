/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeStateTaxDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax-delete-dialog.component';
import { EmployeeStateTaxService } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.service';

describe('Component Tests', () => {

    describe('EmployeeStateTax Management Delete Component', () => {
        let comp: EmployeeStateTaxDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeStateTaxDeleteDialogComponent>;
        let service: EmployeeStateTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeStateTaxDeleteDialogComponent],
                providers: [
                    EmployeeStateTaxService
                ]
            })
            .overrideTemplate(EmployeeStateTaxDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeStateTaxDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeStateTaxService);
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
