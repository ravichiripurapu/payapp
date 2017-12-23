/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyStateTaxDialogComponent } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax-dialog.component';
import { CompanyStateTaxService } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.service';
import { CompanyStateTax } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.model';

describe('Component Tests', () => {

    describe('CompanyStateTax Management Dialog Component', () => {
        let comp: CompanyStateTaxDialogComponent;
        let fixture: ComponentFixture<CompanyStateTaxDialogComponent>;
        let service: CompanyStateTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyStateTaxDialogComponent],
                providers: [
                    CompanyStateTaxService
                ]
            })
            .overrideTemplate(CompanyStateTaxDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyStateTaxDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyStateTaxService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyStateTax(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyStateTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyStateTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyStateTax();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyStateTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyStateTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
