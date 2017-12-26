/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompensationTypeComponent } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.component';
import { CompensationTypeService } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.service';
import { CompensationType } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.model';

describe('Component Tests', () => {

    describe('CompensationType Management Component', () => {
        let comp: CompensationTypeComponent;
        let fixture: ComponentFixture<CompensationTypeComponent>;
        let service: CompensationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompensationTypeComponent],
                providers: [
                    CompensationTypeService
                ]
            })
            .overrideTemplate(CompensationTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompensationTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompensationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompensationType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.compensationTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
