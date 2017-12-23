/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyDepartmentDialogComponent } from '../../../../../../main/webapp/app/entities/company-department/company-department-dialog.component';
import { CompanyDepartmentService } from '../../../../../../main/webapp/app/entities/company-department/company-department.service';
import { CompanyDepartment } from '../../../../../../main/webapp/app/entities/company-department/company-department.model';

describe('Component Tests', () => {

    describe('CompanyDepartment Management Dialog Component', () => {
        let comp: CompanyDepartmentDialogComponent;
        let fixture: ComponentFixture<CompanyDepartmentDialogComponent>;
        let service: CompanyDepartmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyDepartmentDialogComponent],
                providers: [
                    CompanyDepartmentService
                ]
            })
            .overrideTemplate(CompanyDepartmentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDepartmentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDepartmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyDepartment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyDepartment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyDepartmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyDepartment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyDepartment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyDepartmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
