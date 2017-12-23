/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyDepartmentDetailComponent } from '../../../../../../main/webapp/app/entities/company-department/company-department-detail.component';
import { CompanyDepartmentService } from '../../../../../../main/webapp/app/entities/company-department/company-department.service';
import { CompanyDepartment } from '../../../../../../main/webapp/app/entities/company-department/company-department.model';

describe('Component Tests', () => {

    describe('CompanyDepartment Management Detail Component', () => {
        let comp: CompanyDepartmentDetailComponent;
        let fixture: ComponentFixture<CompanyDepartmentDetailComponent>;
        let service: CompanyDepartmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyDepartmentDetailComponent],
                providers: [
                    CompanyDepartmentService
                ]
            })
            .overrideTemplate(CompanyDepartmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDepartmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDepartmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyDepartment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyDepartment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
