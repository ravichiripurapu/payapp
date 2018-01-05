/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyEarningDialogComponent } from '../../../../../../main/webapp/app/entities/company-earning/company-earning-dialog.component';
import { CompanyEarningService } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.service';
import { CompanyEarning } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.model';

describe('Component Tests', () => {

    describe('CompanyEarning Management Dialog Component', () => {
        let comp: CompanyEarningDialogComponent;
        let fixture: ComponentFixture<CompanyEarningDialogComponent>;
        let service: CompanyEarningService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyEarningDialogComponent],
                providers: [
                    CompanyEarningService
                ]
            })
            .overrideTemplate(CompanyEarningDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyEarningDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyEarningService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyEarning(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyEarningType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyEarningListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyEarning();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyEarningType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyEarningListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
