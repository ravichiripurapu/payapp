/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyDepartmentComponent } from '../../../../../../main/webapp/app/entities/company-department/company-department.component';
import { CompanyDepartmentService } from '../../../../../../main/webapp/app/entities/company-department/company-department.service';
import { CompanyDepartment } from '../../../../../../main/webapp/app/entities/company-department/company-department.model';

describe('Component Tests', () => {

    describe('CompanyDepartment Management Component', () => {
        let comp: CompanyDepartmentComponent;
        let fixture: ComponentFixture<CompanyDepartmentComponent>;
        let service: CompanyDepartmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyDepartmentComponent],
                providers: [
                    CompanyDepartmentService
                ]
            })
            .overrideTemplate(CompanyDepartmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDepartmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDepartmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyDepartment(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyDepartments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
