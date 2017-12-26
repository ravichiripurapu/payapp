/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { GenderComponent } from '../../../../../../main/webapp/app/entities/gender/gender.component';
import { GenderService } from '../../../../../../main/webapp/app/entities/gender/gender.service';
import { Gender } from '../../../../../../main/webapp/app/entities/gender/gender.model';

describe('Component Tests', () => {

    describe('Gender Management Component', () => {
        let comp: GenderComponent;
        let fixture: ComponentFixture<GenderComponent>;
        let service: GenderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [GenderComponent],
                providers: [
                    GenderService
                ]
            })
            .overrideTemplate(GenderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GenderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Gender(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.genders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
