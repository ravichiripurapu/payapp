/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { QuarterlyReportsComponent } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.component';
import { QuarterlyReportsService } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.service';
import { QuarterlyReports } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.model';

describe('Component Tests', () => {

    describe('QuarterlyReports Management Component', () => {
        let comp: QuarterlyReportsComponent;
        let fixture: ComponentFixture<QuarterlyReportsComponent>;
        let service: QuarterlyReportsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [QuarterlyReportsComponent],
                providers: [
                    QuarterlyReportsService
                ]
            })
            .overrideTemplate(QuarterlyReportsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuarterlyReportsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuarterlyReportsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new QuarterlyReports(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.quarterlyReports[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
