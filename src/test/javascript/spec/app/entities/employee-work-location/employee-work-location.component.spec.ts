/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeWorkLocationComponent } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.component';
import { EmployeeWorkLocationService } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.service';
import { EmployeeWorkLocation } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.model';

describe('Component Tests', () => {

    describe('EmployeeWorkLocation Management Component', () => {
        let comp: EmployeeWorkLocationComponent;
        let fixture: ComponentFixture<EmployeeWorkLocationComponent>;
        let service: EmployeeWorkLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeWorkLocationComponent],
                providers: [
                    EmployeeWorkLocationService
                ]
            })
            .overrideTemplate(EmployeeWorkLocationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeWorkLocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeWorkLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeWorkLocation(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeWorkLocations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
