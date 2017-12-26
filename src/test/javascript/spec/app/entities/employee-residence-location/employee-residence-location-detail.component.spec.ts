/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeResidenceLocationDetailComponent } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location-detail.component';
import { EmployeeResidenceLocationService } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.service';
import { EmployeeResidenceLocation } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.model';

describe('Component Tests', () => {

    describe('EmployeeResidenceLocation Management Detail Component', () => {
        let comp: EmployeeResidenceLocationDetailComponent;
        let fixture: ComponentFixture<EmployeeResidenceLocationDetailComponent>;
        let service: EmployeeResidenceLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeResidenceLocationDetailComponent],
                providers: [
                    EmployeeResidenceLocationService
                ]
            })
            .overrideTemplate(EmployeeResidenceLocationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeResidenceLocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeResidenceLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeResidenceLocation(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeResidenceLocation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
