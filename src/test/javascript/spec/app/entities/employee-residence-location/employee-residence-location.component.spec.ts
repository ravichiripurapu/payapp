/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeResidenceLocationComponent } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.component';
import { EmployeeResidenceLocationService } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.service';
import { EmployeeResidenceLocation } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.model';

describe('Component Tests', () => {

    describe('EmployeeResidenceLocation Management Component', () => {
        let comp: EmployeeResidenceLocationComponent;
        let fixture: ComponentFixture<EmployeeResidenceLocationComponent>;
        let service: EmployeeResidenceLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeResidenceLocationComponent],
                providers: [
                    EmployeeResidenceLocationService
                ]
            })
            .overrideTemplate(EmployeeResidenceLocationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeResidenceLocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeResidenceLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeResidenceLocation(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeResidenceLocations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
