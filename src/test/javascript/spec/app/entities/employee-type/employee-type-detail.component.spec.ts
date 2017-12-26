/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeTypeDetailComponent } from '../../../../../../main/webapp/app/entities/employee-type/employee-type-detail.component';
import { EmployeeTypeService } from '../../../../../../main/webapp/app/entities/employee-type/employee-type.service';
import { EmployeeType } from '../../../../../../main/webapp/app/entities/employee-type/employee-type.model';

describe('Component Tests', () => {

    describe('EmployeeType Management Detail Component', () => {
        let comp: EmployeeTypeDetailComponent;
        let fixture: ComponentFixture<EmployeeTypeDetailComponent>;
        let service: EmployeeTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeTypeDetailComponent],
                providers: [
                    EmployeeTypeService
                ]
            })
            .overrideTemplate(EmployeeTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
