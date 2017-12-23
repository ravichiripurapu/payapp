/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeTypeComponent } from '../../../../../../main/webapp/app/entities/employee-type/employee-type.component';
import { EmployeeTypeService } from '../../../../../../main/webapp/app/entities/employee-type/employee-type.service';
import { EmployeeType } from '../../../../../../main/webapp/app/entities/employee-type/employee-type.model';

describe('Component Tests', () => {

    describe('EmployeeType Management Component', () => {
        let comp: EmployeeTypeComponent;
        let fixture: ComponentFixture<EmployeeTypeComponent>;
        let service: EmployeeTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeTypeComponent],
                providers: [
                    EmployeeTypeService
                ]
            })
            .overrideTemplate(EmployeeTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
