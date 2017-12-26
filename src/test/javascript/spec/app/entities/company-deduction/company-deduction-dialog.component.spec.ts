/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyDeductionDialogComponent } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction-dialog.component';
import { CompanyDeductionService } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.service';
import { CompanyDeduction } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.model';

describe('Component Tests', () => {

    describe('CompanyDeduction Management Dialog Component', () => {
        let comp: CompanyDeductionDialogComponent;
        let fixture: ComponentFixture<CompanyDeductionDialogComponent>;
        let service: CompanyDeductionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyDeductionDialogComponent],
                providers: [
                    CompanyDeductionService
                ]
            })
            .overrideTemplate(CompanyDeductionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDeductionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDeductionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyDeduction(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyDeduction = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyDeductionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyDeduction();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyDeduction = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyDeductionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
