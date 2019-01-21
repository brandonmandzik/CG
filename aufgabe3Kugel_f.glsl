#version 330

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec3 color;

in vec3  eckenFarben;
in vec3 normalVector;
in vec3 FragPos; 

uniform vec3 lightPos = vec3(200, 100, 100); 
uniform vec3 viewPos= vec3 (0,0,0);
vec3 lightColor = vec3(0.8f, 0.8f, 0.8f);





 
#define MAX(a, b) (((a) > (b)) ? (a) : (b))



 



void main(){
 	float specularStrength = 0.5f;
	
	float ambientStrength = 0.3;
    vec3 ambient = ambientStrength * lightColor;
	

	vec3 tempnormal = normalize(normalVector);
	vec3 lightDir = normalize(lightPos - FragPos);
	
	
	
	float diff = max(dot(tempnormal, lightDir), 0.0);
	vec3 diffuse = diff * lightColor;
	
	vec3 viewDir = normalize(viewPos - FragPos);

	vec3 reflectDir = reflect(-lightDir, tempnormal);

	
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8);
	vec3 specular = specularStrength * spec * vec3(1.0,1.0,1.0); 
	

	vec3 result = ambient + diffuse +  specular;
	
	
	int iter = 2000;
	float max = 200;
	
	vec2 v = vec2(FragPos.x, FragPos.y);
   	float t = 0;
  	while(t < iter) {
    if(length(v) > max) {
      break;
    }
    float v_x = v.x*v.x - v.y*v.y + FragPos.x;
    v.y = 2*v.x*v.y + FragPos.y;
    v.x = v_x-1.85f;
    ++t;
    }
  	vec4 brotcolor = vec4((t / iter), (MAX(t - iter/2, 0) / iter), 0, 0);
	
	
	
	color = brotcolor.xyz+result;
	
}













