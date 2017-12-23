/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeWorkLocationDetailComponent } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location-detail.component';
import { EmployeeWorkLocationService } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.service';
import { EmployeeWorkLocation } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.model';

describe('Component Tests', () => {

    describe('EmployeeWorkLocation Management Detail Component', () => {
        let comp: EmployeeWorkLocationDetailComponent;
        let fixture: ComponentFixture<EmployeeWorkLocationDetailComponent>;
        let service: EmployeeWorkLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeWorkLocationDetailComponent],
                providers: [
                    EmployeeWorkLocationService
                ]
            })
            .overrideTemplate(EmployeeWorkLocationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeWorkLocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeWorkLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeWorkLocation(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeWorkLocation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
